import Card from "../../molecules/card/Card";
import Image from "../../atoms/image/Image";
import Button from "../../atoms/button/Button";
import "../../styles/pages/posts/post.css";
import CardPost from "../../components/card-post/CardPost";
import CardComment from "../../components/card-comments/CardComments.jsx";
import CardSpecs from "../../components/card-specs/CardSpecs.jsx";
import {validateTokenWithRole,} from "../../utilities/jwt-utilities.js";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

const Post = () => {
    const {id} = useParams();
    const [post, setPost] = useState({});
    const [comments, setComments] = useState([]);
    const [specs, setSpecs] = useState([]);
    const [similar, setSimilar] = useState([]);

    validateTokenWithRole("CLIENTE");

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
        ktronix:
            "https://femac.coop/images/2023/servicios/convenios/ktronix.png",
        falabella:
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/Falabella.svg/2560px-Falabella.svg.png",
        "mercado-libre":
            "https://tecnomarketingnews.com/wp-content/uploads/2016/08/MercadoLibre.png",
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const pResponse = await fetch(
                    "http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1/product/find-by-id",
                    {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: "Bearer " + localStorage.getItem("token"),
                        },
                        body: JSON.stringify({
                            productoId: id,
                        }),
                    }
                );

                if (pResponse.status === 403) {
                    localStorage.clear();
                    window.location.href = "/login";
                } else if (!pResponse.ok) {
                    alert("Hubo un error al recuperar los datos");
                    return;
                }

                const productData = await pResponse.json();
                setPost(productData.producto);

                setComments(productData.producto.comentarios);
                setSpecs(productData.producto.caracteristicas);

                setSimilar(productData.similar);
            } catch (error) {
                alert("Hubo un error al recuperar los datos");
            }
        };

        fetchData();
    }, [id]);

    return (
        <>
            <div className="center">
                <br/>
                <div className="or">
                    <h2 className="post-title">{post.nombre}</h2>
                </div>
                <br/>
                <div className="line"/>
            </div>
            <div className="post">
                <Card>
                    <div className="e-commerce">
                        <Image
                            src={platformLogos[post.plataforma]}
                            alt={post.nombre}
                            width={"30%"}
                        />
                    </div>
                    <div className="cover-image">
                        <Image src={post.imagenUrl} alt={post.nombre} height={"300px"}/>
                    </div>
                </Card>
                <Card padding={"30px"}>
                    <br/>
                    <p className="postDesc">{formatCurrency(post.precio)}</p>
                    <br/>
                    <p className="category"><b>Categoría: </b>{post.categoria ? post.categoria.replace(/\b\w/g, (char) => char.toUpperCase()) : ""}</p>
                    <p className="platform"><b>Plataforma: </b>{post.plataforma ? post.plataforma.replace(/\b\w/g, (char) => char.toUpperCase()) : ""}</p>
                    <p className="brand"><b>Marca: </b>{post.marca ? post.marca.replace(/\b\w/g, (char) => char.toUpperCase()) : ""}</p>
                    <a href={post.url} target="_blank" rel="noreferrer noopener">
                        <Button
                            margin={"20px 0 0 0"}
                            text={"Ir a la tienda"}
                            width={"100%"}
                        />
                    </a>
                </Card>
            </div>
            <div className="specifications">
                <Card width={"80%"} padding={"30px"}>
                    <h2 className="title">Especificaciones</h2>
                    <div className="wrap">
                        {specs.map((spec) => (
                            <CardSpecs key={spec._id} spec={spec}/>
                        ))}
                    </div>
                </Card>
            </div>
            <div className="center">
                <br/>
                <div className="or">Similares</div>
                <br/>
                <div className="line"/>
            </div>
            <div className="post">
                {similar.map((post) => (
                    <CardPost key={post._id} post={post}/>
                ))}
            </div>
            <div className="center">
                <br/>
                <div className="or">Comentarios</div>
                <br/>
                <div className="line"/>
            </div>
            <div className="post">
                {comments.map((comment) => (
                    <CardComment key={comment._id} comment={comment}/>
                ))}
            </div>
        </>
    );
};

export default Post;
