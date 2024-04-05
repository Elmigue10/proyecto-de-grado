package umb.v1.informationandproductmanagement.business.service.interfaces;

import umb.v1.informationandproductmanagement.domain.model.entity.PqrsRequestEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.PqrsRequestUserEntity;

public interface IPqrsRequestService {

    PqrsRequestEntity savePqrsRequestEntity(PqrsRequestEntity pqrsRequest,
                                            PqrsRequestUserEntity pqrsRequestUserForUser,
                                            PqrsRequestUserEntity pqrsRequestUserForAdmin);

    void updatePqrsRequestUpdatePassword(Long pqrsId);
}
