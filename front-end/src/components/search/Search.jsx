import "../../styles/components/search/search.css";
import { useState } from "react";
import SearchIcon from "../../assest/search.png";
import Image from "../../atoms/image/Image";

const Search = () => {
  const [search, setSearch] = useState("");

  const handleSearch = async (e) => {
    e.preventDefault();

    window.location.href = `/search?search=${search}`;
  };

  return (
    <>
      <form className="search" onSubmit={handleSearch}>
        <input
          className="search-input"
          type="text"
          placeholder="Buscar producto tecnológico..."
          onChange={(e) => setSearch(e.target.value)}
        />
        <Image
          src={SearchIcon}
          width={"30px"}
          height={"30px"}
          margin={"0"}
          cursor={"pointer"}
          onClick={handleSearch}
        />
      </form>
    </>
  );
};

export default Search;
