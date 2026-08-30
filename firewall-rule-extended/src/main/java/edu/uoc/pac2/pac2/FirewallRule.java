package edu.uoc.pac2.pac2;

import java.time.LocalDate;

/**
 * Represents a firewall rule with source/destination IP, protocol, port, action and validity controls.
 * <p>
 * The class validates inputs through setters and throws checked {@link Exception} with a specific
 * message when a value is invalid. Helper methods allow checking rule criticality, expiration,
 * applicability against traffic tuples and computing allowed/denied traffic percentages.
 * </p>
 *
 * <h2>Validation highlights</h2>
 * <ul>
 *   <li>IP format: simplified IPv4 pattern {@code d.d.d.d} with each {@code d} in {@code 0..999}.</li>
 *   <li>Protocols: {@code TCP}, {@code UDP}, {@code ICMP} (case-insensitive on input, stored uppercase).</li>
 *   <li>Ports: {@code 1..65535}.</li>
 *   <li>Actions: {@code ALLOW}, {@code DENY} (case-insensitive on input, stored uppercase).</li>
 *   <li>Dates: {@code creationDate} must be today or past; {@code validUntil} must be
 *       on/after {@code creationDate}.</li>
 * </ul>
 */
public class FirewallRule {

    // ---------------------------------------------------------------------------------------------
    // Instance attributes
    // ---------------------------------------------------------------------------------------------

    /** Human-readable rule name. */
    private String ruleName;

    /** Source IPv4 address (simplified validation). */
    private String sourceIP;

    /** Destination IPv4 address (simplified validation). */
    private String destinationIP;

    /** Transport/network protocol (TCP/UDP/ICMP). Stored uppercase. */
    private String protocol;

    /** Destination port number (1..65535). */
    private int port;

    /** Action to perform (ALLOW or DENY). Stored uppercase. */
    private String action;

    /** Flag that enables/disables the rule. */
    private boolean isEnabled;

    /** Rule creation date (must not be in the future). */
    private LocalDate creationDate;

    /** Rule validity end date (must be on/after {@link #creationDate}). */
    private LocalDate validUntil;

    // ---------------------------------------------------------------------------------------------
    // Static attributes (not final, per exercise constraints)
    // ---------------------------------------------------------------------------------------------

    /** Minimum valid port value. */
    private static int minPort = 1;

    /** Maximum valid port value. */
    private static int maxPort = 65535;

    /** FTP well-known port. */
    private static int ftpPort = 21;

    /** SSH well-known port. */
    private static int sshPort = 22;

    /** HTTP well-known port. */
    private static int httpPort = 80;

    /** HTTPS well-known port. */
    private static int httpsPort = 443;

    /** List of valid protocol names. */
    private static String[] validProtocols = {"TCP", "UDP", "ICMP"};

    /** List of valid actions. */
    private static String[] validActions = {"ALLOW", "DENY"};

    // ---------------------------------------------------------------------------------------------
    // Error message literals
    // ---------------------------------------------------------------------------------------------

    /** Error message for invalid rule name. */
    private static final String ERR_NAME   = "[ERROR] Rule name cannot be null, empty or contain only whitespace.";
    /** Error message for invalid source IP. */
    private static final String ERR_SRCIP  = "[ERROR] Source IP must be a valid IPv4 address.";
    /** Error message for invalid destination IP. */
    private static final String ERR_DSTIP  = "[ERROR] Destination IP must be a valid IPv4 address.";
    /** Error message for invalid protocol. */
    private static final String ERR_PROTO  = "[ERROR] Protocol must be one of: TCP, UDP, ICMP.";
    /** Error message for invalid port. */
    private static final String ERR_PORT   = "[ERROR] The port number must be greater than or equal to 1 and less than or equal to 65535.";
    /** Error message for invalid action. */
    private static final String ERR_ACTION = "[ERROR] Action must be one of: ALLOW, DENY.";
    /** Error message for invalid creation date. */
    private static final String ERR_CDATE  = "[ERROR] Creation date cannot be null or in the future.";
    /** Error message for invalid valid-until date. */
    private static final String ERR_VDATE  = "[ERROR] Valid until date cannot be null or before the creation date.";
    /** Error message for invalid inputs in traffic percentage calculations. */
    private static final String ERR_TRAFP  = "[ERROR] Invalid input data for traffic percentage calculation.";

    // ---------------------------------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------------------------------

    /**
     * Builds a new {@code FirewallRule} with the provided parameters and validates them through the
     * corresponding setters.
     *
     * @param ruleName      rule name (non-null, non-blank)
     * @param sourceIP      source IPv4 address (simplified format)
     * @param destinationIP destination IPv4 address (simplified format)
     * @param protocol      protocol (TCP/UDP/ICMP), case-insensitive
     * @param port          port in range [1..65535]
     * @param action        action (ALLOW/DENY), case-insensitive
     * @param isEnabled     whether the rule is enabled
     * @param creationDate  creation date (not in the future)
     * @param validUntil    validity end date (on/after {@code creationDate})
     * @throws Exception if any parameter is invalid
     */
    public FirewallRule(String ruleName,
                        String sourceIP,
                        String destinationIP,
                        String protocol,
                        int port,
                        String action,
                        boolean isEnabled,
                        LocalDate creationDate,
                        LocalDate validUntil) throws Exception {

        setRuleName(ruleName);
        setSourceIP(sourceIP);
        setDestinationIP(destinationIP);
        setProtocol(protocol);
        setPort(port);
        setAction(action);
        setEnabled(isEnabled);
        // Important: creation date first, then validUntil
        setCreationDate(creationDate);
        setValidUntil(validUntil);
    }

    // ---------------------------------------------------------------------------------------------
    // Getters
    // ---------------------------------------------------------------------------------------------

    /**
     * Gets the rule name.
     *
     * @return the rule name
     */
    public String getRuleName() { return this.ruleName; }

    /**
     * Gets the source IP address.
     *
     * @return the source IP address
     */
    public String getSourceIP() { return this.sourceIP; }

    /**
     * Gets the destination IP address.
     *
     * @return the destination IP address
     */
    public String getDestinationIP() { return this.destinationIP; }

    /**
     * Gets the protocol (always uppercase).
     *
     * @return the protocol
     */
    public String getProtocol() { return this.protocol; }

    /**
     * Gets the destination port.
     *
     * @return the port number
     */
    public int getPort() { return this.port; }

    /**
     * Gets the action (always uppercase).
     *
     * @return the action
     */
    public String getAction() { return this.action; }

    /**
     * Indicates whether the rule is enabled.
     *
     * @return {@code true} if enabled; {@code false} otherwise
     */
    public boolean isEnabled() { return this.isEnabled; }

    /**
     * Gets the creation date.
     *
     * @return the creation date
     */
    public LocalDate getCreationDate() { return this.creationDate; }

    /**
     * Gets the validity end date.
     *
     * @return the validity end date
     */
    public LocalDate getValidUntil() { return this.validUntil; }

    // ---------------------------------------------------------------------------------------------
    // Setters (throwing checked Exception)
    // ---------------------------------------------------------------------------------------------

    /**
     * Sets the rule name after trimming white spaces.
     *
     * @param ruleName non-null, non-blank name
     * @throws Exception if {@code ruleName} is {@code null}, empty or only whitespace
     */
    public void setRuleName(String ruleName) throws Exception {
        if (ruleName == null || ruleName.trim().isEmpty()) {
            throw new Exception(ERR_NAME);
        }
        this.ruleName = ruleName.trim();
    }

    // ---------------------------------------------------------------------------------------------
    // Private helpers
    // ---------------------------------------------------------------------------------------------

    /**
     * Checks whether a string matches a simplified IPv4 pattern: {@code d.d.d.d} with each
     * {@code d} being {@code 0..999}.
     *
     * @param ip string to validate
     * @return {@code true} if it matches the pattern; {@code false} otherwise
     */
    private boolean isValidIP(String ip) {
        if (ip == null) return false;
        return ip.matches("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
    }

    /**
     * Sets the source IP address.
     *
     * @param sourceIP IPv4 address in simplified format
     * @throws Exception if the IP is invalid
     */
    public void setSourceIP(String sourceIP) throws Exception {
        if (!isValidIP(sourceIP)) {
            throw new Exception(ERR_SRCIP);
        }
        this.sourceIP = sourceIP;
    }

    /**
     * Sets the destination IP address.
     *
     * @param destinationIP IPv4 address in simplified format
     * @throws Exception if the IP is invalid
     */
    public void setDestinationIP(String destinationIP) throws Exception {
        if (!isValidIP(destinationIP)) {
            throw new Exception(ERR_DSTIP);
        }
        this.destinationIP = destinationIP;
    }

    /**
     * Validates a protocol against the allowed list (case-insensitive).
     *
     * @param protocol protocol name to validate
     * @return {@code true} if allowed; {@code false} otherwise
     */
    private boolean isValidProtocol(String protocol) {
        if (protocol == null) return false;
        for (String p : validProtocols) {
            if (p.equalsIgnoreCase(protocol)) return true;
        }
        return false;
    }

    /**
     * Sets the protocol, storing it in uppercase.
     *
     * @param protocol TCP/UDP/ICMP (case-insensitive)
     * @throws Exception if the protocol is not valid
     */
    public void setProtocol(String protocol) throws Exception {
        if (!isValidProtocol(protocol)) {
            throw new Exception(ERR_PROTO);
        }
        this.protocol = protocol.toUpperCase();
    }

    /**
     * Sets the destination port number.
     *
     * @param port value in [1..65535]
     * @throws Exception if outside the valid range
     */
    public void setPort(int port) throws Exception {
        if (port < minPort || port > maxPort) {
            throw new Exception(ERR_PORT);
        }
        this.port = port;
    }

    /**
     * Validates an action name against the allowed list (case-insensitive).
     *
     * @param action action to validate
     * @return {@code true} if action is allowed; {@code false} otherwise
     */
    private boolean isValidAction(String action) {
        if (action == null) return false;
        for (String a : validActions) {
            if (a.equalsIgnoreCase(action.trim())) return true;
        }
        return false;
    }

    /**
     * Sets the action, storing it in uppercase.
     *
     * @param action ALLOW/DENY (case-insensitive)
     * @throws Exception if the action is not valid
     */
    public void setAction(String action) throws Exception {
        if (!isValidAction(action)) {
            throw new Exception(ERR_ACTION);
        }
        this.action = action.trim().toUpperCase();
    }

    /**
     * Enables or disables the rule.
     *
     * @param isEnabled {@code true} to enable; {@code false} to disable
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Sets the creation date (must not be in the future).
     *
     * @param creationDate date to set
     * @throws Exception if {@code null} or after today
     */
    public void setCreationDate(LocalDate creationDate) throws Exception {
        if (creationDate == null || creationDate.isAfter(LocalDate.now())) {
            throw new Exception(ERR_CDATE);
        }
        this.creationDate = creationDate;
    }

    /**
     * Sets the validity end date. It must not be {@code null} and must not be before
     * {@link #creationDate}.
     *
     * @param validUntil validity end date
     * @throws Exception if {@code null} or before {@code creationDate}
     */
    public void setValidUntil(LocalDate validUntil) throws Exception {
        if (validUntil == null || this.creationDate == null || validUntil.isBefore(this.creationDate)) {
            throw new Exception(ERR_VDATE);
        }
        this.validUntil = validUntil;
    }

    // ---------------------------------------------------------------------------------------------
    // Business logic
    // ---------------------------------------------------------------------------------------------

    /**
     * Checks whether the rule has not expired (i.e., today is on/before {@link #validUntil}).
     *
     * @return {@code true} if still valid; {@code false} otherwise
     */
    public boolean isNotExpired() {
        return this.validUntil != null && !LocalDate.now().isAfter(this.validUntil);
    }

    /**
     * Checks whether the {@link #port} is one of the typical critical ports: 21, 22, 80, 443.
     *
     * @return {@code true} if the port is critical; {@code false} otherwise
     */
    public boolean isCriticalPort() {
        return this.port == ftpPort || this.port == sshPort || this.port == httpPort || this.port == httpsPort;
    }

    /**
     * A rule is considered critical if it is enabled, not expired and uses a critical port.
     *
     * @return {@code true} if the rule meets all criticality conditions; {@code false} otherwise
     * @see #isEnabled()
     * @see #isNotExpired()
     * @see #isCriticalPort()
     */
    public boolean isCriticalRule() {
        return this.isEnabled && isNotExpired() && isCriticalPort();
    }

    /**
     * Checks whether the rule is applicable to a given traffic tuple. The rule must be enabled,
     * not expired, and all fields must match exactly.
     *
     * @param sourceIP      source IP to check
     * @param destinationIP destination IP to check
     * @param protocol      protocol to check (must match stored uppercase value)
     * @param port          port to check
     * @return {@code true} if applicable; {@code false} otherwise
     */
    public boolean isApplicable(String sourceIP, String destinationIP, String protocol, int port) {
        if (!this.isEnabled || !isNotExpired()) return false;
        return this.sourceIP != null && this.destinationIP != null && this.protocol != null
                && this.sourceIP.equals(sourceIP)
                && this.destinationIP.equals(destinationIP)
                && this.protocol.equals(protocol)
                && this.port == port;
    }

    /**
     * Counts how many traffic tuples match this rule and also match the provided {@code action}.
     * The arrays are positional: index {@code i} represents one complete tuple.
     * Invalid inputs produce zero matches.
     *
     * @param sourceIP      array of source IPs
     * @param destinationIP array of destination IPs
     * @param protocol      array of protocols
     * @param port          array of ports
     * @param action        action to compare against (ALLOW/DENY)
     * @return number of matches found
     */
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

    /**
     * Validates that input arrays are non-null, of equal length and each element complies with
     * the validations for IP, protocol and port. Empty arrays are considered valid.
     *
     * @param sourceIP      array of source IPs
     * @param destinationIP array of destination IPs
     * @param protocol      array of protocols
     * @param port          array of ports
     * @return {@code true} if all inputs are valid; {@code false} otherwise
     */
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

    /**
     * Computes the percentage of tuples in which this rule is applicable and the rule's action
     * equals {@code ALLOW}.
     *
     * @param sourceIP      array of source IPs
     * @param destinationIP array of destination IPs
     * @param protocol      array of protocols
     * @param port          array of ports
     * @return percentage in {@code [0,100]} (0 if arrays are empty)
     * @throws Exception if inputs are invalid
     */
    public double calculateAllowedTrafficPct(String[] sourceIP,
                                             String[] destinationIP,
                                             String[] protocol,
                                             int[] port) throws Exception {

        if (!areInputsValid(sourceIP, destinationIP, protocol, port)) {
            throw new Exception(ERR_TRAFP);
        }
        if (sourceIP.length == 0) return 0.0;

        int allowed = countMatchingRulesByAction(sourceIP, destinationIP, protocol, port, "ALLOW");
        return (allowed * 100.0) / sourceIP.length;
    }

    /**
     * Computes the percentage of tuples in which this rule is applicable and the rule's action
     * equals {@code DENY}.
     *
     * @param sourceIP      array of source IPs
     * @param destinationIP array of destination IPs
     * @param protocol      array of protocols
     * @param port          array of ports
     * @return percentage in {@code [0,100]} (0 if arrays are empty)
     * @throws Exception if inputs are invalid
     */
    public double calculateDeniedTrafficPct(String[] sourceIP,
                                            String[] destinationIP,
                                            String[] protocol,
                                            int[] port) throws Exception {

        if (!areInputsValid(sourceIP, destinationIP, protocol, port)) {
            throw new Exception(ERR_TRAFP);
        }
        if (sourceIP.length == 0) return 0.0;

        int denied = countMatchingRulesByAction(sourceIP, destinationIP, protocol, port, "DENY");
        return (denied * 100.0) / sourceIP.length;
    }
}
