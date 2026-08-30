package edu.uoc.pac4.notification;

public class MultipleNotificationService implements Notifier {

    private final EmailNotifier emailNotifier;
    private final SmsNotifier smsNotifier;

    public MultipleNotificationService(EmailNotifier emailNotifier,
                                       SmsNotifier smsNotifier) {
        this.emailNotifier = emailNotifier;
        this.smsNotifier = smsNotifier;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        emailNotifier.sendEmail(to, subject, body);
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        smsNotifier.sendSms(phoneNumber, message);
    }
}