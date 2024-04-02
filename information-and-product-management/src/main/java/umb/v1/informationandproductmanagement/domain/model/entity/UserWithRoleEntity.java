package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.ROLE;

@Getter
@Entity
@Table(name = "usuario")
public class UserWithRoleEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Column(name = "fecha_actualiza")
    private Date fechaActualiza;

    @Column(name = "estado")
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private RoleEntity role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE + "_" + role.getRol()));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return estado;
    }

    @Override
    public boolean isAccountNonLocked() {
        return estado;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return estado;
    }

    @Override
    public boolean isEnabled() {
        return estado;
    }
}
