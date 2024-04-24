import { Link } from "react-router-dom";
import Button from "../../atoms/button/Button";
import Card from "../../molecules/card/Card";
import Image from "../../atoms/image/Image";
import "../../styles/components/card-post/card-post.css";

const CardPost = ({ post }) => {
  const formatCurrency = (amount) => {
    const formatter = new Intl.NumberFormat("es-CO", {
      style: "currency",
      currency: "COP",
      minimumFractionDigits: 0,
    });
    return formatter.format(amount);
  };

  const platformLogos = {
    exito:
      "https://seeklogo.com/images/E/exito-logo-4AC4CFF6A0-seeklogo.com.png",
    ktronix: "https://femac.coop/images/2023/servicios/convenios/ktronix.png",
    falabella:
      "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/Falabella.svg/2560px-Falabella.svg.png",
    "mercado-libre":
      "https://tecnomarketingnews.com/wp-content/uploads/2016/08/MercadoLibre.png",
  };

  return (
    <>
      <Card className="card-responsive" margin={"0"}>
        <Link className="link" to={`/post/${post._id}`}>
          <div className="product-info">
            <div className="post-title">
              <span className="title">{post.nombre}</span>
            </div>
            <Image
              src={platformLogos[post.plataforma]}
              alt={post.nombre}
              width={"30%"}
            />
            <div className="cover-image">
              <Image src={post.imagenUrl} alt={post.nombre} height={"300px"} />
            </div>
          </div>
          <div className="price-and-desc">
            <p className="desc">{formatCurrency(post.precio)}</p>
            <Button className="cardButton" text="Ver producto" />
          </div>
        </Link>
      </Card>
    </>
  );
};

export default CardPost;
