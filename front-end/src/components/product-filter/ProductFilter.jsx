import {Link} from "react-router-dom";
import "../../styles/components/product-filter/product-filter.css"

const ProductFilter = () => {
    return (
        <div className="product-filter">
            <Link className="title-filter" to="/cellphones">
                Celulares
            </Link>
            <Link className="title-filter" to="/computers">
                Computadores
            </Link>
            <Link className="title-filter" to="/tablets">
                Tablets
            </Link>
            <Link className="title-filter" to="/monitors">
                Monitores
            </Link>
            <Link className="title-filter" to="/others">
                Otros
            </Link>
        </div>
    );
};

export default ProductFilter;
