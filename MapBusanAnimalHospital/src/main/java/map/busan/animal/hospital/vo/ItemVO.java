package map.busan.animal.hospital.vo;

import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemVO {
    private String gugun;
    private String animal_hospital;
    private String approval;
    private String road_address;
    private String tel;
    private String lat;
    private String lon;
    private String basic_date;
}
