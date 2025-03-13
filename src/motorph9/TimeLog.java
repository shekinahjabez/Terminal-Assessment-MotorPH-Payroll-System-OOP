package motorph9;

import java.time.LocalDateTime;

public class TimeLog {
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;

    public TimeLog(LocalDateTime timeIn, LocalDateTime timeOut) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalDateTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
    }
}