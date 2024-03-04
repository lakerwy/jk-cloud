package vip.xiaonuo.pan.modular.userFile.event;

import org.springframework.context.ApplicationEvent;

public class UserDevLogHandleEvent extends ApplicationEvent {
    public UserDevLogHandleEvent(UserDevLogEvent userDevLogEvent) {
        super(userDevLogEvent);
    }
}
