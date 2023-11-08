package vip.xiaonuo.sys.modular.hr.conference.rez.event;

import org.springframework.context.ApplicationEvent;
import vip.xiaonuo.sys.modular.hr.conference.rez.vo.ConferenceMsgEvent;

public class ConferenceMsgHandle extends ApplicationEvent {
    public ConferenceMsgHandle(ConferenceMsgEvent conferenceMsgEvent) {
        super(conferenceMsgEvent);
    }

}
