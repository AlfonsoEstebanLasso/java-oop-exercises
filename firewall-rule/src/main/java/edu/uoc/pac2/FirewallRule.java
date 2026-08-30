package edu.uoc.pac2;

import java.time.LocalDate;

public class FirewallRule {

    private String ruleName;
    private String sourceIP;
    private String destinationIP;
    private String protocol;
    private int port;
    private String action;
    private boolean isEnabled;
    private LocalDate creationDate;
    private LocalDate validUntil;

    private static int minPort = 1;
    private static int maxPort = 65535;

    private static int ftpPort = 21;
    private static int sshPort = 22;
    private static int httpPort = 80;
    private static int httpsPort = 443;

    private static String[] validProtocols = {"TCP", "UDP", "ICMP"};
    private static String[] validActions = {"ALLOW", "DENY"};

    public FirewallRule(String ruleName,
                        String sourceIP,
                        String destinationIP,
                        String protocol,
                        int port,
                        String action,
                        boolean isEnabled,
                        LocalDate creationDate,
                        LocalDate validUntil) {

        setRuleName(ruleName);
        setSourceIP(sourceIP);
        setDestinationIP(destinationIP);
        setProtocol(protocol);
        setPort(port);
        setAction(action);
        setEnabled(isEnabled);
        setCreationDate(creationDate);
        setValidUntil(validUntil);
    }


    public String getRuleName() { return this.ruleName; }
    public String getSourceIP() { return this.sourceIP; }
    public String getDestinationIP() { return this.destinationIP; }
    public String getProtocol() { return this.protocol; }
    public int getPort() { return this.port; }
    public String getAction() { return this.action; }
    public boolean isEnabled() { return this.isEnabled; }
    public LocalDate getCreationDate() { return this.creationDate; }
    public LocalDate getValidUntil() { return this.validUntil; }


    public void setRuleName(String ruleName) {
        if (ruleName == null || ruleName.trim().isEmpty()) {
            System.out.println("[ERROR] Rule name cannot be null, empty or contain only whitespace.");
            return;
        }
        this.ruleName = ruleName.trim();
    }

    private boolean isValidIP(String ip) {
        if (ip == null) return false;
        // d.d.d.d, donde d = 0..999 (validación simplificada)
        return ip.matches("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
    }

    public void setSourceIP(String sourceIP) {
        if (!isValidIP(sourceIP)) {
            System.out.println("[ERROR] Source IP must be a valid IPv4 address.");
            return;
        }
        this.sourceIP = sourceIP;
    }

    public void setDestinationIP(String destinationIP) {
        if (!isValidIP(destinationIP)) {
            System.out.println("[ERROR] Destination IP must be a valid IPv4 address.");
            return;
        }
        this.destinationIP = destinationIP;
    }

    private boolean isValidProtocol(String protocol) {
        if (protocol == null) return false;
        for (String p : validProtocols) {
            if (p.equalsIgnoreCase(protocol)) return true;
        }
        return false;
    }

    public void setProtocol(String protocol) {
        if (!isValidProtocol(protocol)) {
            System.out.println("[ERROR] Protocol must be one of: TCP, UDP, ICMP.");
            return;
        }
        this.protocol = protocol.toUpperCase();
    }

    public void setPort(int port) {
        if (port < minPort || port > maxPort) {
            System.out.println("[ERROR] The port number must be greater than or equal to 1 and less than or equal to 65535.");
            return;
        }
        this.port = port;
    }

    private boolean isValidAction(String action) {
        if (action == null) return false;
        for (String a : validActions) {
            if (a.equalsIgnoreCase(action.trim())) return true;
        }
        return false;
    }

    public void setAction(String action) {
        if (!isValidAction(action)) {
            System.out.println("[ERROR] Action must be one of: ALLOW, DENY.");
            return;
        }
        this.action = action.trim().toUpperCase();
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setCreationDate(LocalDate creationDate) {
        if (creationDate == null || creationDate.isAfter(LocalDate.now())) {
            System.out.println("[ERROR] Creation date cannot be null or in the future.");
            return;
        }
        this.creationDate = creationDate;
    }

    public void setValidUntil(LocalDate validUntil) {
        if (validUntil == null || this.creationDate == null || validUntil.isBefore(this.creationDate)) {
            System.out.println("[ERROR] Valid until date cannot be null or before the creation date.");
            return;
        }
        this.validUntil = validUntil;
    }

    public boolean isNotExpired() {
        return this.validUntil != null && !LocalDate.now().isAfter(this.validUntil);
    }

    public boolean isCriticalPort() {
        return this.port == ftpPort || this.port == sshPort || this.port == httpPort || this.port == httpsPort;
    }

    public boolean isCriticalRule() {
        return this.isEnabled && isNotExpired() && isCriticalPort();
    }

    public boolean isApplicable(String sourceIP, String destinationIP, String protocol, int port) {
        if (!this.isEnabled || !isNotExpired()) return false;
        return this.sourceIP != null && this.destinationIP != null && this.protocol != null
                && this.sourceIP.equals(sourceIP)
                && this.destinationIP.equals(destinationIP)
                && this.protocol.equals(protocol)
                && this.port == port;
    }

    public int countMatchingRulesByAction(String[] sourceIP,
                                          String[] destinationIP,
                                          String[] protocol,
                                          int[] port,
                                          String action) {

        if (!areInputsValid(sourceIP, destinationIP, protocol, port) || !isValidAction(action)) {
            return 0;
        }

        int n = sourceIP.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (isApplicable(sourceIP[i], destinationIP[i], protocol[i], port[i])
                    && this.action != null
                    && this.action.equalsIgnoreCase(action)) {
                count++;
            }
        }

        return count;
    }

    private boolean areInputsValid(String[] sourceIP,
                                   String[] destinationIP,
                                   String[] protocol,
                                   int[] port) {

        if (sourceIP == null || destinationIP == null || protocol == null || port == null) return false;

        int n = sourceIP.length;
        if (destinationIP.length != n || protocol.length != n || port.length != n) return false;

        for (int i = 0; i < n; i++) {
            if (!isValidIP(sourceIP[i])) return false;
            if (!isValidIP(destinationIP[i])) return false;
            if (!isValidProtocol(protocol[i])) return false;
            if (port[i] < minPort || port[i] > maxPort) return false;
        }

        return true;
    }

    public double calculateAllowedTrafficPct(String[] sourceIP,
                                             String[] destinationIP,
                                             String[] protocol,
                                             int[] port) {

        if (!areInputsValid(sourceIP, destinationIP, protocol, port) || sourceIP.length == 0) {
            System.out.println("[ERROR] Invalid input data for traffic percentage calculation.");
            return 0.0;
        }

        int allowed = countMatchingRulesByAction(sourceIP, destinationIP, protocol, port, "ALLOW");
        return (allowed * 100.0) / sourceIP.length;
    }

    public double calculateDeniedTrafficPct(String[] sourceIP,
                                            String[] destinationIP,
                                            String[] protocol,
                                            int[] port) {

        if (!areInputsValid(sourceIP, destinationIP, protocol, port) || sourceIP.length == 0) {
            System.out.println("[ERROR] Invalid input data for traffic percentage calculation.");
            return 0.0;
        }

        int denied = countMatchingRulesByAction(sourceIP, destinationIP, protocol, port, "DENY");
        return (denied * 100.0) / sourceIP.length;
    }
}
