import CardPqrsCustomer from "../../components/card-pqrs/CardPqrsCustomer";
import {
  extractEmailFromToken,
  validateTokenWithRole,
} from "../../utilities/jwt-utilities";
import { useEffect, useState } from "react";

const CustomerPqrs = () => {
  validateTokenWithRole("CLIENTE");

  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const itemsPerPage = 9;
  const [pqrs, setPqrs] = useState([]);

  const token = localStorage.getItem("token");
  const email = extractEmailFromToken(token);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const skip = (currentPage - 1) * itemsPerPage;
        const pqrsResponse = await fetch(
          `http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1/pqrs/find-by-email?correoElectronico=${email}&skip=${skip}&limit=${itemsPerPage}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!pqrsResponse.ok) {
          alert("Hubo un error al recuperar los datos");
          return;
        } else if (pqrsResponse.status === 403) {
          localStorage.clear();
          window.location.href = "/login";
        }

        const pqrsData = await pqrsResponse.json();
        setPqrs(pqrsData.pqrs);
        const totalPqrs = pqrsData.total;
        console.log(totalPqrs);
        const calculatedTotalPages = Math.ceil(totalPqrs / itemsPerPage);
        setTotalPages(Math.max(calculatedTotalPages, 1));
      } catch (error) {
        alert("Hubo un error al recuperar los datos");
      }
    };

    fetchData();
  }, [currentPage, token, email]);

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
    <>
      <div className="center">
        <div className="or">Mis solicitudes</div>
        <div className="line" />
      </div>

      <div className="home admin-pqrs-container">
        {pqrs.map((item) => (
          <CardPqrsCustomer key={item.id} pqrs={item} />
        ))}
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
    </>
  );
};

export default CustomerPqrs;
