package org.taskly.taskly_backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.taskly.taskly_backend.config.security.JwtService;
import org.taskly.taskly_backend.exception.custom.ResourceAlreadyExistsException;
import org.taskly.taskly_backend.exception.custom.ResourceNotFoundException;
import org.taskly.taskly_backend.role.RoleRepository;
import org.taskly.taskly_backend.user.User;
import org.taskly.taskly_backend.user.UserRepository;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegistrationRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email " + request.email() + " already exists!");
        }

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role with name: USER not found!"));

        // falta por as relacoes
        var user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .dateOfBirth(request.dateOfBirth())
                .jobRole(request.jobRole())
                .photoUrl(request.photoUrl())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .build();

        if(request.photoUrl() == null) {
            // todo - guardar o path para uma foto padrao
            // else guardar o path
        }

        userRepository.save(user);
    }

    /*public void processPhoto(Long userId, MultipartFile photoFile, String photoUrl) {
        String photoPath;

        // Define o diretório onde as fotos serão armazenadas
        String uploadDir = "src/main/resources/static/uploads/users/";

        // Verifica se o diretório existe e cria caso não exista
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (photoUrl == null || photoUrl.isEmpty()) {
            // Foto padrão
            photoPath = uploadDir + "default.png"; // Nome do arquivo da foto padrão
            System.out.println("Foto padrão será usada: " + photoPath);
        } else {
            try {
                // Obtém o tipo de arquivo e verifica se é válido
                String originalFileName = photoFile.getOriginalFilename();
                String fileExtension = "";

                if (originalFileName != null && originalFileName.contains(".")) {
                    fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                }

                // Verifica extensões permitidas
                if (!fileExtension.matches("\\.(png|jpg|jpeg)$")) {
                    throw new IllegalArgumentException("Formato de arquivo não suportado!");
                }

                // Cria o nome do arquivo baseado no ID do usuário
                String fileName = "profile-" + userId + fileExtension;

                // Define o caminho completo do arquivo
                photoPath = uploadDir + fileName;

                // Salva o arquivo no diretório
                File targetFile = new File(photoPath);
                photoFile.transferTo(targetFile);

                System.out.println("Foto salva com sucesso: " + photoPath);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao salvar a foto: " + e.getMessage(), e);
            }
        }
    }*/

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (userRepository.findByEmail(request.email()).isEmpty()) {
            throw new ResourceNotFoundException("Email " + request.email() + " not found!");
        }

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullname());
        var jwtToken = jwtService.generateToken(claims, user);
        return new AuthenticationResponse(jwtToken);
    }
}