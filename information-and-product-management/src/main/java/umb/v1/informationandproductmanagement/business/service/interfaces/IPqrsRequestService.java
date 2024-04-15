package umb.v1.informationandproductmanagement.business.service.interfaces;

import umb.v1.informationandproductmanagement.domain.model.dto.PqrsRequestDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.PqrsRequestResponseDTO;
import umb.v1.informationandproductmanagement.domain.model.entity.PqrsRequestEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.PqrsRequestUserEntity;

public interface IPqrsRequestService {

    PqrsRequestEntity savePqrsRequestEntity(PqrsRequestEntity pqrsRequest,
                                            PqrsRequestUserEntity pqrsRequestUserForUser,
                                            PqrsRequestUserEntity pqrsRequestUserForAdmin);

    void updatePqrsRequestUpdatePassword(Long pqrsId);

    PqrsRequestResponseDTO findAll(int skip, int limit);

    PqrsRequestResponseDTO save(PqrsRequestDTO pqrsRequest);

    PqrsRequestResponseDTO update(PqrsRequestDTO pqrsRequest);

    PqrsRequestResponseDTO findByEmail(String correoElectronico, int skip, int limit);
}
