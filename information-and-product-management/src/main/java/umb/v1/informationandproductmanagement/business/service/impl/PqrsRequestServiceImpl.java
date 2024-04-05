package umb.v1.informationandproductmanagement.business.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.service.interfaces.IPqrsRequestService;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.entity.PqrsRequestEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.PqrsRequestUserEntity;
import umb.v1.informationandproductmanagement.domain.repository.PqrsRequestRepository;
import umb.v1.informationandproductmanagement.domain.repository.PqrsRequestUserRepository;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Slf4j
public class PqrsRequestServiceImpl implements IPqrsRequestService {

    private final PqrsRequestRepository pqrsRequestRepository;
    private final PqrsRequestUserRepository pqrsRequestUserRepository;

    public PqrsRequestServiceImpl(PqrsRequestRepository pqrsRequestRepository,
                                  PqrsRequestUserRepository pqrsRequestUserRepository) {
        this.pqrsRequestRepository = pqrsRequestRepository;
        this.pqrsRequestUserRepository = pqrsRequestUserRepository;
    }

    @Override
    public void savePqrsRequestEntity(PqrsRequestEntity pqrsRequest,
                                      PqrsRequestUserEntity pqrsRequestUserForUser,
                                      PqrsRequestUserEntity pqrsRequestUserForAdmin) {
        PqrsRequestEntity pqrsRequestSaved = pqrsRequestRepository.save(pqrsRequest);

        pqrsRequestUserForUser.setSolicitudPqrsId(pqrsRequestSaved.getId());
        pqrsRequestUserForAdmin.setSolicitudPqrsId(pqrsRequestSaved.getId());
        pqrsRequestUserRepository.save(pqrsRequestUserForUser);
        pqrsRequestUserRepository.save(pqrsRequestUserForAdmin);
    }

    @Override
    public void updatePqrsRequestUpdatePassword(Long id, String cambioContraena) {
        Optional<PqrsRequestEntity> optionalPqrsRequest =
                pqrsRequestRepository.findByUserIdAndDescription(cambioContraena, id);

        if (optionalPqrsRequest.isPresent()) {
            PqrsRequestEntity pqrsRequest = optionalPqrsRequest.get();

            pqrsRequest.setFechaActualiza(new Timestamp(System.currentTimeMillis()));
            pqrsRequest.setIncidenciaId(4L);
        } else {
            throw new ApiException("Solicitud PQRS no encontrada", 404);
        }

    }


}
