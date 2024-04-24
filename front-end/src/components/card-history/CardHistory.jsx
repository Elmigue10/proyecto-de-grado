import Card from "../../molecules/card/Card";
import { Link } from "react-router-dom";

const CardHistory = ({ search }) => {

    return (
        <Card margin={"0"}>
            <Link className="link" to={`/search?search=${search.busqueda}`}>
                <h1 className={"card-pqrs-title"}>Busqueda:</h1>
                <p className={"card-pqrs-text"}>{search.busqueda}</p>
                <h1 className={"card-pqrs-title"}>Fecha:</h1>
                <p className={"card-pqrs-text"}>{search.fecha}</p>
            </Link>
        </Card>
    );
}

export default CardHistory;