package vip.xiaonuo.oa.modular.attendance.group.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransformPosition {
  
  private double lng;
  private double lat;

  public TransformPosition() {}

  public TransformPosition(double lng, double lat) {
    this.lng = lng;
    this.lat = lat;
  }


}
