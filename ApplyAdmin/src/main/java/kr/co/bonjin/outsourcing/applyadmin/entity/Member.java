package kr.co.bonjin.outsourcing.applyadmin.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.bonjin.outsourcing.applyadmin.entity.common.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @JoinColumn : 외래키를 매핑할 때 사용.
 *              JoinColumn 의 name 속성에는 매핑할 외래키 이름을 지정
 *              어노테이션 생략가능. 생략한다면 외래키로 매핑되는 컬럼의 이름은 필드평+'_'+참조하는 테이블의 컬럼명 예) user_user_no
 *
 * @OneToMany : 일대다 관계라는 매핑 정보로 일대다 연관관계를 매핑할 때 필수 사용
 *              targetEntity : 관계를 맺을 Entity Class 를 정의
 *              cascade : 현 Entity 의 변경에 대해 관계를 맺은 Entity 도 변경 전략을 결정
 *                      속성값은 CascadeType 라는 enum 에 정의 되어 있으며
 *                      enum 값에는 ALL. PERSIST, MERGE, REMOVE, REFRESH, DETACH 가 있음
 *              fetch : 관계 Entity 의 데이터 읽기 전략을 결정
 *                      FetchType.EAGER, FetchType.LAZY 전략을 변경 할 수 있음
 *                      EAGER 는 관계된 Entity 의 정보를 미리 읽음
 *                      LAZY 는 실제로 사용하는 순간 읽어옴
 *              mappedBy : 양방향 관계 설정시 관계의 주체가 되는 쪽에서 정의
 *              orphanRemoval : 관계 Entity 에서 변경이 일어난 경우 DB 변경을 같이 할지 결정
 *                              cascade 와 다른것은 cascade 는 JPA 레이어 수준이고
 *                              이것은 DB 레이어에서 처리.
 *                              Default 는 false
 *
 * @ManyToOne : 다대일 관계라는 매핑 정보로 다대일 연관관계를 매핑할 때 필수 사용
 *
 *              mapperBy : 양방향 매핑일 때 사용. 반대쪽 필드 이름을 값으로 입력
 *              항상 1:N, N:1 관계에서는 항상 N쪽에 외래키가 있음
 *              주인은 mapperBy 속성을 사용하지 않음.
 *              주인이 아니면 mapperBy 속성을 사용해서 속성의 값으로 연관관계의 주인을 지정
 *              외래키 관리자를 선택
 *              연관관계 주인만이 데이터베이스 연관관계와 매핑되어 외래키를 관리(등록, 수정, 삭제) 할 수 있음
 *              주인이 아닌쪽은 읽기만 할 수 있음
 *
 *              optional : false 설정시 null 이 들어갈 수 있음. 반드시 값이 필요하면 true (Default - true)
 *
 * @ManyToOne, @OneToOne 어노테이션의 경우 FetchType.EAGER ( 즉시로딩 )을 기본 전략으로 사용
 * @OneToMany, @ManyToMany 어노테이션의 경우 FetchType.Lazy ( 지연로딩 )을 기본 전략으로 사용
 */
@Builder // builder 를 사용할수 있게 합니다.
@Entity // jpa entity 임을 알립니다.
@Getter // 필드값의 getter 를 자동으로 생성합니다.
@Setter
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다. (JPA 필수)
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@ToString
@Table(name = "member") // 'admin' 테이블과 매핑됨을 명시
public class Member extends BaseEntity implements UserDetails {

    // 회원번호
    @Id // primaryKey 임을 알립니다.
    @Column(name = "member_id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // pk 생성전략을 DB에 위임한다는 의미.
    // mysql 로 보면 pk 필드를 auto_increment 로 설정해 놓은 경우로 보면 된다.
    // Oracle 사용시 Sequence 로 바꾸기
    private Long id;

    // 아이디
    @Column(name = "username")
    private String username;

    // 비밀번호
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    // 별명
    @Column(name = "nickname")
    private String nickname;

    // 이메일
    @Column(name = "email")
    private String email;

    // 핸드폰번호
    @Column(name = "phone")
    private String phone;

    // 권한은 회원당 여러 개가 세팅될 수 있으므로 Collection 으로 선언
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default // Lombodk의 @Builder를 사용하다보면 기본값으로 null이 들어가게 되는데 @Builder.Default 어노테이션을 이용하여 기본값을 설정할 수 있음
    // 단방향
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", nullable = false)
    // 양방향
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Role> role;

    public void addRole(Role role) {
        if (this.role == null) {
            this.role = new ArrayList<>();
        }
        this.role.add(role);
    }





    /******************
     UserDetails Override functions
     ******************/
    /**
     * 사용자에게 부여된 권한
     * @JsonIgnore 응답시 무시
     *  or
     * @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 쓰기만 가능
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.stream()
                .map(m -> new SimpleGrantedAuthority(m.getRole()))
                .collect(Collectors.toList());
    }

    /**
     * 사용자 인증에 사용된 이름
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 사용자 계정 만료 여부
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 사용자가 잠김 여부
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 사용자의 자격증명(암호)이 만료 여부
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자의 사용가능여부
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
