package umb.v1.informationandproductmanagement.business.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.service.interfaces.IPqrsRequestService;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.dto.PqrsRequestDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.PqrsRequestResponseDTO;
import umb.v1.informationandproductmanagement.domain.model.entity.*;
import umb.v1.informationandproductmanagement.domain.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.*;

@Service
@Slf4j
public class PqrsRequestServiceImpl implements IPqrsRequestService {

    private final PqrsRequestRepository pqrsRequestRepository;
    private final PqrsRequestUserRepository pqrsRequestUserRepository;
    private final UserWithRoleRepository userWithRoleRepository;
    private final IncidenceRepository incidenceRepository;
    private final PqrsRequestTypeRepository pqrsRequestTypeRepository;


    public PqrsRequestServiceImpl(PqrsRequestRepository pqrsRequestRepository,
                                  PqrsRequestUserRepository pqrsRequestUserRepository,
                                  UserWithRoleRepository userWithRoleRepository,
                                  IncidenceRepository incidenceRepository,
                                  PqrsRequestTypeRepository pqrsRequestTypeRepository) {
        this.pqrsRequestRepository = pqrsRequestRepository;
        this.pqrsRequestUserRepository = pqrsRequestUserRepository;
        this.userWithRoleRepository = userWithRoleRepository;
        this.incidenceRepository = incidenceRepository;
        this.pqrsRequestTypeRepository = pqrsRequestTypeRepository;
    }

    @Override
    public PqrsRequestEntity savePqrsRequestEntity(PqrsRequestEntity pqrsRequest,
                                                   PqrsRequestUserEntity pqrsRequestUserForUser,
                                                   PqrsRequestUserEntity pqrsRequestUserForAdmin) {
        PqrsRequestEntity pqrsRequestSaved = pqrsRequestRepository.save(pqrsRequest);

        pqrsRequestUserForUser.setSolicitudPqrsId(pqrsRequestSaved.getId());
        pqrsRequestUserForAdmin.setSolicitudPqrsId(pqrsRequestSaved.getId());
        pqrsRequestUserRepository.save(pqrsRequestUserForUser);
        pqrsRequestUserRepository.save(pqrsRequestUserForAdmin);

        return pqrsRequestSaved;
    }

    @Override
    public void updatePqrsRequestUpdatePassword(Long pqrsId) {
        Optional<PqrsRequestEntity> optionalPqrsRequest = pqrsRequestRepository.findById(pqrsId);

        if (optionalPqrsRequest.isPresent()) {
            PqrsRequestEntity pqrsRequest = optionalPqrsRequest.get();

            pqrsRequest.setFechaActualiza(new Timestamp(System.currentTimeMillis()));
            pqrsRequest.setIncidenciaId(4L);
            pqrsRequestRepository.save(pqrsRequest);
        } else {
            throw new ApiException("Solicitud PQRS no encontrada", 404);
        }

    }

    @Override
    public PqrsRequestResponseDTO findAll() {
        List<PqrsRequestEntity> pqrsRequests = pqrsRequestRepository.findAll();

        List<PqrsRequestDTO> pqrsRequestsDTO = new ArrayList<>();
        pqrsRequests.forEach(request -> {

            List<PqrsRequestUserEntity> pqrsRequestUserEntities =
                    pqrsRequestUserRepository.findBySolicitudPqrsId(request.getId());

            List<UserWithRoleEntity> userWithRoleEntities = new ArrayList<>();
            pqrsRequestUserEntities.forEach(pqrsRequestUser -> userWithRoleEntities
                    .add(userWithRoleRepository.findById(pqrsRequestUser.getUsuarioId())
                            .orElse(null)));

            String email = "";

            for (UserWithRoleEntity userWithRoleEntity : userWithRoleEntities) {
                if (userWithRoleEntity == null) {
                    continue;
                }
                if (userWithRoleEntity.getRole().getRol().equals(CLIENTE)) {
                    email = userWithRoleEntity.getCorreoElectronico();
                }
            }

            IncidenceEntity incidence = incidenceRepository.findById(request.getIncidenciaId())
                    .orElse(new IncidenceEntity());

            PqrsRequestTypeEntity pqrsRequestType = pqrsRequestTypeRepository.findById(request.getTipoSolicitudPqrsId())
                    .orElse(new PqrsRequestTypeEntity());

            pqrsRequestsDTO.add(PqrsRequestDTO.builder()
                    .id(request.getId())
                    .descripcionSolicitud(request.getDescripcionSolicitud())
                    .fechaRegistro(request.getFechaRegistro().toString())
                    .fechaActualiza(request.getFechaActualiza() == null ?
                            null :
                            request.getFechaActualiza().toString())
                    .correoElectronico(email)
                    .incidencia(incidence.getTipoIncidencia())
                    .tipoSolicitud(pqrsRequestType.getTipoSolicitud())
                    .build());
        });

        return PqrsRequestResponseDTO.builder()
                .message(OK)
                .status(200)
                .pqrsRequest(pqrsRequestsDTO)
                .build();
    }

    @Override
    public PqrsRequestResponseDTO save(PqrsRequestDTO pqrsRequest) {

        UserWithRoleEntity user = userWithRoleRepository.findByCorreoElectronico(pqrsRequest.getCorreoElectronico())
                .orElseThrow(() -> new ApiException("Usuario no encontrado", 404));

        savePqrsRequestEntity(PqrsRequestEntity.builder()
                        .descripcionSolicitud(pqrsRequest.getDescripcionSolicitud())
                        .fechaRegistro(new Timestamp(System.currentTimeMillis()))
                        .tipoSolicitudPqrsId(2L)
                        .incidenciaId(2L)
                        .build(),
                PqrsRequestUserEntity.builder()
                        .usuarioId(user.getId())
                        .build(),
                PqrsRequestUserEntity.builder()
                        .usuarioId(1L)
                        .build());

        return PqrsRequestResponseDTO.builder()
                .message(OK)
                .status(200)
                .build();
    }

    @Override
    public PqrsRequestResponseDTO update(PqrsRequestDTO pqrsRequest) {
        PqrsRequestEntity pqrsRequestEntity = pqrsRequestRepository.findById(pqrsRequest.getId())
                .orElseThrow(() -> new ApiException("Solicitud PQRS no encontrada", 404));

        pqrsRequestEntity.setIncidenciaId(4L);
        pqrsRequestEntity.setFechaActualiza(new Timestamp(System.currentTimeMillis()));

        pqrsRequestRepository.save(pqrsRequestEntity);

        return PqrsRequestResponseDTO.builder()
                .message(OK)
                .status(200)
                .build();
    }

    @Override
    public PqrsRequestResponseDTO findByEmail(String correoElectronico) {
        UserWithRoleEntity user = userWithRoleRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new ApiException("Usuario no encontrado", 404));

        List<PqrsRequestEntity> pqrsRequestEntities = pqrsRequestRepository.findByUserId(user.getId());

        List<PqrsRequestDTO> pqrsRequests = new ArrayList<>();
        pqrsRequestEntities.forEach(pqrsRequest -> {

            IncidenceEntity incidence = incidenceRepository.findById(pqrsRequest.getIncidenciaId())
                    .orElse(new IncidenceEntity());

            PqrsRequestTypeEntity pqrsRequestType = pqrsRequestTypeRepository.findById(pqrsRequest.getTipoSolicitudPqrsId())
                    .orElse(new PqrsRequestTypeEntity());

            pqrsRequests.add(PqrsRequestDTO.builder()
                    .id(pqrsRequest.getId())
                    .correoElectronico(user.getCorreoElectronico())
                    .fechaRegistro(pqrsRequest.getFechaRegistro().toString())
                    .fechaActualiza(pqrsRequest.getFechaActualiza() == null ?
                            null :
                            pqrsRequest.getFechaActualiza().toString())
                    .incidencia(incidence.getTipoIncidencia())
                    .tipoSolicitud(pqrsRequestType.getTipoSolicitud())
                    .build());
        });

        return PqrsRequestResponseDTO.builder()
                .message(OK)
                .status(200)
                .pqrsRequest(pqrsRequests)
                .build();
    }


}
