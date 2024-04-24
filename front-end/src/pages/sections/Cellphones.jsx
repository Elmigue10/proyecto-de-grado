import ProductFilter from "../../components/product-filter/ProductFilter";
import Search from "../../components/search/Search";
import FilterBy from "../../components/filter-by/FilterBy";
import CardPost from "../../components/card-post/CardPost";
import "../../styles/pages/sections/sections.css";
import { validateTokenWithRole } from "../../utilities/jwt-utilities.js";
import { useEffect, useState } from "react";
import {
  findAllBySection,
  findAllByPriceAndSection,
  findAllByRamAndSection,
  findAllByStorageAndSection,
  findAllByBrandAndSection,
  findAllByPlatformAndSection,
} from "../../utilities/findSections.js";

const Cellphones = () => {
  const [filterOption, setFilterOpt] = useState(0);
  const section = "smartphone";
  const [filter, setFilter] = useState({});
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const itemsPerPage = 6;

  validateTokenWithRole("CLIENTE");

  // Función para recibir los datos filtrados del componente de filtro
  const handleFilteredData = (data) => {
    setFilterOpt(data.filterOption);
    setFilter(data.filter);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        var totalProducts = 0;
        const skip = (currentPage - 1) * itemsPerPage;
        if (filterOption === 0) {
          const pRes = await findAllBySection(section, skip, itemsPerPage);
          totalProducts = pRes.totalProducts;
          setProducts(pRes.products);
        }
        if (filterOption === 1) {
          const pRes = await findAllByRamAndSection(
            section,
            filter.selectedRAM,
            skip,
            itemsPerPage
          );
          totalProducts = pRes.totalProducts;
          setProducts(pRes.products);
        }
        if (filterOption === 2) {
          const pRes = await findAllByStorageAndSection(
            section,
            filter.selectedDisco,
            skip,
            itemsPerPage
          );
          totalProducts = pRes.totalProducts;
          setProducts(pRes.products);
        }
        if (filterOption === 3) {
          const pRes = await findAllByPriceAndSection(
            section,
            filter.min,
            filter.max,
            skip,
            itemsPerPage
          );
          totalProducts = pRes.totalProducts;
          setProducts(pRes.products);
        }
        if (filterOption === 5) {
          const pRes = await findAllByBrandAndSection(
            section,
            filter.selectedMarca,
            skip,
            itemsPerPage
          );
          totalProducts = pRes.totalProducts;
          setProducts(pRes.products);
        }
        if (filterOption === 6) {
          const pRes = await findAllByPlatformAndSection(
            section,
            filter.selectedPlataforma,
            skip,
            itemsPerPage
          );
          totalProducts = pRes.totalProducts;
          setProducts(pRes.products);
        }

        const calculatedTotalPages = Math.ceil(totalProducts / itemsPerPage);
        setTotalPages(Math.max(calculatedTotalPages, 1));
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [currentPage, filter, filterOption]);

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
      <Search />
      <ProductFilter />
      <div className="center">
        <div className="or">Celulares</div>
        <div className="line" />
      </div>
      <div className="devices">
        <FilterBy onFilteredData={handleFilteredData} section={section} />
        <div className="devices-cards">
          {products.map((post) => (
            <CardPost key={post._id} post={post} />
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
    </>
  );
};

export default Cellphones;
