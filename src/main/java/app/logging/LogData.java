package app.logging;

import lombok.Data;

import java.time.LocalTime;

@Data
public class LogData {

    private final String logMessage;
    private LocalTime addTime;

    public LogData(String logMessage) {
        this.logMessage=logMessage;
        this.addTime = LocalTime.now();
    }
}
