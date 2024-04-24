import {useEffect, useState} from "react";
import {validateTokenWithRole} from "../../utilities/jwt-utilities";
import CardPost from "../../components/card-post/CardPost";

const CustomerViews = () => {
    const [products, setProducts] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const itemsPerPage = 15;

    validateTokenWithRole("CLIENTE");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const skip = (currentPage - 1) * itemsPerPage;
                const pResponse = await fetch(
                    `http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1/user/viewed-products?skip=${skip}&limit=${itemsPerPage}`,
                    {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: "Bearer " + localStorage.getItem("token"),
                        },
                    }
                );

                if (pResponse.status === 403) {
                    localStorage.clear();
                    window.location.href = "/login";
                } else if (!pResponse.ok) {
                    alert("Hubo un error al recuperar los datos");
                    return;
                }

                const productsData = await pResponse.json();
                setProducts(productsData.productos);

                const totalProducts = productsData.totalProductos;
                const calculatedTotalPages = Math.ceil(totalProducts / itemsPerPage);
                setTotalPages(Math.max(calculatedTotalPages, 1));
            } catch (error) {
                alert("Hubo  un error al recuperar los datos");
            }
        };

        fetchData();
    }, [currentPage]);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const goToFirstPage = () => {
        setCurrentPage(1);
    };

    const goToLastPage = () => {
        setCurrentPage(totalPages);
    };

    const renderPageButtons = () => {
        const pageButtons = [];
        const totalPagesToShow = 5;

        let startPage = Math.max(1, currentPage - Math.floor(totalPagesToShow / 2));
        let endPage = Math.min(totalPages, startPage + totalPagesToShow - 1);

        if (endPage - startPage < totalPagesToShow - 1) {
            startPage = Math.max(1, endPage - totalPagesToShow + 1);
        }

        for (let i = startPage; i <= endPage; i++) {
            pageButtons.push(
                <button
                    key={i}
                    onClick={() => handlePageChange(i)}
                    className={currentPage === i ? "active" : ""}
                >
                    {i}
                </button>
            );
        }

        return pageButtons;
    };

    return (
        <div className="homepage">
            <div className="center">
                <div className="or">Mis visualizaciones</div>
                <div className="line"/>
            </div>
            <div className="devices">
                <div className="devices-cards">
                    {products.map((post) => (
                        <CardPost key={post._id} post={post}/>
                    ))}
                </div>
            </div>
            <div className="pagination">
                <button onClick={goToFirstPage} disabled={currentPage === 1}>
                    {"<<"}
                </button>
                <button
                    onClick={() => handlePageChange(currentPage - 1)}
                    disabled={currentPage === 1}
                >
                    {"<"}
                </button>
                {renderPageButtons()}
                <button
                    onClick={() => handlePageChange(currentPage + 1)}
                    disabled={currentPage === totalPages}
                >
                    {">"}
                </button>
                <button onClick={goToLastPage} disabled={currentPage === totalPages}>
                    {">>"}
                </button>
            </div>
        </div>
    );
}

export default CustomerViews;