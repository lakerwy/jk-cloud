package vip.xiaonuo.sys.modular.im.bean;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/14 11:09
 */
@Setter
@Getter
public class ImResult {

    private String ActionStatus;

    private Integer ErrorCode;

    private String ErrorInfo;

    private T data;

}
