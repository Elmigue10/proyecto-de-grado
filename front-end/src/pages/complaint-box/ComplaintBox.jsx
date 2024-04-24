import Button from "../../atoms/button/Button";
import Check from "../../assest/check.png";
import {useState} from "react";
import {extractEmailFromToken, validateTokenWithRole} from "../../utilities/jwt-utilities";

const ComplaintBox = () => {

    validateTokenWithRole("CLIENTE");

    const [complaintSent, setComplaintSend] = useState(false);
    const [complaintContent, setComplaintContent] = useState("");

    const token = localStorage.getItem("token");
    const handleComplaintBox = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch(
                `http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1/pqrs/save`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "authorization": `Bearer ${token}`
                    },
                    body: JSON.stringify({
                        descripcionSolicitud: complaintContent,
                        correoElectronico: extractEmailFromToken(token)
                    })
                }
            );

            if (response.ok) {
                console.log("pqrs enviado");
                setComplaintSend(true);
            } else {
                alert("Error al crear la solicitud...");
            }
        } catch (error) {
            console.error("Error al enviar pqrs:", error);
            alert("Hubo un problema al crear la solicitud pqrs.");
        }
    }

    return (
        <div>
            {!complaintSent ? (
                <div className="complaint-box">
                    <h1 className="title">Buzón de quejas</h1>
                    <p className="complaint-box-text">
                        Aquí podrás escribirnos cualquier queja, sugerencia o comentario sobre la aplicación.
                        Tu opinión es muy importante para nosotros y nos ayuda a mejorar continuamente.
                    </p>
                    <div className="complaint-box-wrapper">
                        <div className="right">
                            <form
                                className="complaint-box-form"
                                onSubmit={handleComplaintBox}
                            >
                                <div>
                                    <textarea className="complaint-box-text-area"
                                              rows="10"
                                              cols="35"
                                              placeholder="Dejanos tus comentarios..."
                                              onChange={(e) => setComplaintContent(e.target.value)}
                                              required></textarea>
                                </div>
                                <Button
                                    alignSelf="center"
                                    text="Enviar"
                                    width={"120px"}
                                />
                            </form>
                        </div>
                    </div>
                </div>
            ) : (
                <div className="complaint-box">
                    <img src={Check} alt="" className="mail-send-check-img"/>
                    <h1 className="title">Recibimos tus comentarios</h1>
                    <p className="complaint-box-text">
                        Nuestro equipo los revisará cuidadosamente y tomará las medidas necesarias para abordar
                        tus comentarios.
                    </p>
                </div>
            )}
        </div>
    );
}

export default ComplaintBox;