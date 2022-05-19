package com.example.demo.servicios;

import com.example.demo.entidades.Direccion;
import com.example.demo.entidades.Foto;
import com.example.demo.entidades.Usuario;
import com.example.demo.enumeraciones.Rol;
import com.example.demo.excepciones.ExcepcionPropia;
import com.example.demo.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Usuario guardar(String email, String nombre, String apellido, Date fechaNacimiento, Direccion direccion, String clave, MultipartFile archivo) throws Exception {

        // VALIDACIONES
        validar(email, nombre, apellido, fechaNacimiento, direccion, clave);

        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setDireccion(direccion);
        usuario.setEstaActivo(Boolean.TRUE);
        usuario.setRol(Rol.USUARIO);

        String claveEncriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(claveEncriptada);
        
        Foto foto = fotoServicio.multiPartToEntity(archivo);
        usuario.setFoto(foto);

        return usuarioRepositorio.save(usuario);
    }

    public void validar(String email, String nombre, String apellido, Date fechaNacimiento, Direccion direccion, String clave) throws Exception {

        if (email == null || email.trim().isEmpty()) {
            throw new ExcepcionPropia("NO PUEDE SER NULO ESTE VALOR");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ExcepcionPropia("NO PUEDE SER NULO ESTE VALOR");
        }

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ExcepcionPropia("NO PUEDE SER NULO ESTE VALOR");
        }

        if (fechaNacimiento == null || fechaNacimiento.toString().trim().isEmpty()) {
            throw new ExcepcionPropia("NO PUEDE SER NULO ESTE VALOR");
        }

        if (direccion == null || direccion.toString().isEmpty()) {
            throw new ExcepcionPropia("NO PUEDE SER NULO ESTE VALOR");
        }

        if (clave == null || clave.trim().isEmpty() || clave.length() < 8) {
            throw new ExcepcionPropia("NO PUEDE SER NULO ESTE VALOR");
        }
    }

    
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.encontrarPorEmail(mail);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
                        
            // Concateno la informacion del STRING del ENUM del rol del usuario
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol());
            permisos.add(p1);
         
            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getClave(), permisos);
            return user;

        } else {
            return null;
        }

    }
}
