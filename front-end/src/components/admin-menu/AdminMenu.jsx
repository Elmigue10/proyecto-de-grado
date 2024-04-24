import { Link } from "react-router-dom";

const ProductFilter = () => {
    return (
        <div className="product-filter admin-menu">
            <Link className="title-filter" to="/admin-web-scraper">
                Busqueda de productos en la web
            </Link>
            <Link className="title-filter" to="/admin-pqrs">
               PQRS 
            </Link>
        </div>
    )
}
export default ProductFilter;