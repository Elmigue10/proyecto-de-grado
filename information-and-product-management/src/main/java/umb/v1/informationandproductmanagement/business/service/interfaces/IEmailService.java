package umb.v1.informationandproductmanagement.business.service.interfaces;

public interface IEmailService {
    void sendMail(String correoElectronico, String restorePasswordRequest, String content);
}
