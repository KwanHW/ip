import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public interface Timeable {
    public LocalDate getDate();

    public String getDateString(DateTimeFormatter dateTimeFormat);

    public static LocalDate of(String date) throws DateTimeParseException {
        return LocalDate.parse(date);
    }

    public static DateTimeFormatter getPrintableFormat() {
        return DateTimeFormatter.ofPattern("MMM dd yyyy");
    }

    public static DateTimeFormatter getWritableFormat() {return DateTimeFormatter.ofPattern("yyyy-MM-dd"); };
}
