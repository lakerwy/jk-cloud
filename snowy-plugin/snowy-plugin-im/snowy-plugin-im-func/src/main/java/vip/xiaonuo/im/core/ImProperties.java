package vip.xiaonuo.im.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/14 10:06
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "im")
public class ImProperties {


    private boolean enable;

    private String url;

    private String code;

    public String admin_url;
    public String admin_secret;

    public String admin_user_id;

    boolean use_random_name;
    String welcome_for_new_user;
    String welcome_for_back_user;

    boolean new_user_robot_friend;
    String robot_friend_id;
    String robot_welcome;

    String prompt_text;
    String image_msg_url;
    String image_msg_base64_thumbnail;

    String new_user_subscribe_channel_id;
    String back_user_subscribe_channel_id;
}
