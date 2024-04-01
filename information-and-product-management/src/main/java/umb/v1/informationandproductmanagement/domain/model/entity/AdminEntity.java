package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.*;

@Builder
@Getter
@Entity
@Table(name = "administrador")
public class AdminEntity implements UserDetails {

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

    public AdminEntity(Long id, String nombreCompleto, String correoElectronico, String contrasena) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    public AdminEntity() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE + "_" + ADMIN));
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
