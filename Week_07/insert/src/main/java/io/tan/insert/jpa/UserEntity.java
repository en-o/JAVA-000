package io.tan.insert.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author tn
 * @version 1
 * @ClassName UserEntity
 * @description
 * @date 2020/12/2 23:42
 */
@Entity
@Table(name="tb_user2")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Long uuid;
    private String userName;
    private String email;
    private String phone    ;

}
