public class ContactFormatter {
    public static String format(String name, String phone) {
        String formattedPhone = phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6);
        return String.format("%s | %s", name, formattedPhone);
    }
}