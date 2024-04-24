import Image from "../../atoms/image/Image";
import Card from "../../molecules/card/Card";
import "../../styles/pages/about-us/about-us.css";
import Diego from "../../assest/diego.jpg";
import Juan from "../../assest/juan.jpg";
import Migue from "../../assest/migue.jpg";

const AboutUs = () => {
  return (
    <>
      <br />
      <div className="center">
        <div className="or">Acerca de nosotros</div>
        <div className="line" />
      </div>
      <div className="aboutus-content">
        <div className="about-us">
          <p>
            <b>Bienvenido a Comparatech</b><br /><br />
          </p>
          <p>
            Somos tu destino definitivo para comparar productos tecnológicos
            fácilmente. Desde teléfonos celulares hasta computadoras y más,
            nuestra plataforma simplifica tu búsqueda al ofrecerte una
            comparación clara de especificaciones clave. Además, ofrecemos un
            espacio para que presentes tus preguntas y quejas, y mantenemos
            nuestra base de datos actualizada mediante tecnología de scraping
            web. En Comparatech, tu satisfacción es nuestra prioridad.
          </p>
        </div>
        <div className="devs">
          <Card>
            <h2>Miguel Valbuena</h2>
            <div className="dev-image">
              <Image src={Migue} height={"300px"} />
            </div>
            <div className="dev-description">
              <p>Miguel Angel Valbuena</p>
              <p>Estudiante de ingeniería de Software.</p>
              <p>Universidad Manuela Beltrán.</p>
            </div>
          </Card>
          <Card>
            <h2>Juan Castañeda</h2>
            <div className="dev-image">
              <Image src={Juan} height={"300px"} />
            </div>
            <div className="dev-description">
              <p>Juan David Castañeda</p>
              <p>Estudiante de ingeniería de Software.</p>
              <p>Universidad Manuela Beltrán.</p>
            </div>
          </Card>
          <Card>
            <h2>Diego Mayorga</h2>
            <div className="dev-image">
              <Image src={Diego} height={"300px"} />
            </div>
            <div className="dev-description">
              <p> Diego Alejandro Mayorga</p>
              <p>Estudiante de ingeniería de Software.</p>
              <p>Universidad Manuela Beltrán.</p>
            </div>
          </Card>
        </div>
      </div>
    </>
  );
};

export default AboutUs;
