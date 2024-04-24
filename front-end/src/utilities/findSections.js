const awsDns = "http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1";

const validResponse = (pResponse) => {
  if (pResponse.status === 403) {
    localStorage.clear();
    window.location.href = "/login";
  } else if (!pResponse.ok) {
    alert("Hubo un error al recuperar los datos");
    return;
  }
};

export const findAllBySection = async (section, skip, itemsPerPage) => {
  const pResponse = await fetch(
    `${awsDns}/product/find-by-category?category_name=${section}&skip=${skip}&limit=${itemsPerPage}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    }
  );

  validResponse(pResponse);
  let productsData = {};
  const prod = await pResponse.json();
  productsData.products = prod.productos;
  productsData.totalProducts = prod.totalProductos;
  return productsData;
};

export const findAllByRamAndSection = async (
  section,
  selectedRAM,
  skip,
  itemsPerPage
) => {
  const pResponse = await fetch(
    `${awsDns}/product/find-by-ram-memory?ram_memory=${selectedRAM}&category_name=${section}&skip=${skip}&limit=${itemsPerPage}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    }
  );

  validResponse(pResponse);
  let productsData = {};
  const prod = await pResponse.json();
  productsData.products = prod.productos;
  productsData.totalProducts = prod.totalProductos;
  return productsData;
};

export const findAllByStorageAndSection = async (
  section,
  selectedDisco,
  skip,
  itemsPerPage
) => {
  const pResponse = await fetch(
    `${awsDns}/product/find-by-storage-capacity?storage_capacity=${selectedDisco}&category_name=${section}&skip=${skip}&limit=${itemsPerPage}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    }
  );

  validResponse(pResponse);
  let productsData = {};
  const prod = await pResponse.json();
  productsData.products = prod.productos;
  productsData.totalProducts = prod.totalProductos;
  return productsData;
};

export const findAllByPriceAndSection = async (
  section,
  min,
  max,
  skip,
  itemsPerPage
) => {
  const pResponse = await fetch(
    `${awsDns}/product/find-by-price-range?min_price=${min}&max_price=${max}&category_name=${section}&skip=${skip}&limit=${itemsPerPage}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    }
  );

  validResponse(pResponse);
  let productsData = {};
  const prod = await pResponse.json();
  productsData.products = prod.productos;
  productsData.totalProducts = prod.totalProductos;
  return productsData;
};

export const findAllByScreenSizeAndSection = async (
  section,
  selectedPantalla,
  skip,
  itemsPerPage
) => {
  const pResponse = await fetch(
    `${awsDns}/product/find-by-screen-size?screen_size=${selectedPantalla}&category_name=${section}&skip=${skip}&limit=${itemsPerPage}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    }
  );

  validResponse(pResponse);
  let productsData = {};
  const prod = await pResponse.json();
  productsData.products = prod.productos;
  productsData.totalProducts = prod.totalProductos;
  return productsData;
};

export const findAllByBrandAndSection = async (
  section,
  selectedMarca,
  skip,
  itemsPerPage
) => {
  const pResponse = await fetch(
    `${awsDns}/product/find-by-brand-and-category?brand_name=${selectedMarca}&category_name=${section}&skip=${skip}&limit=${itemsPerPage}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    }
  );

  validResponse(pResponse);
  let productsData = {};
  const prod = await pResponse.json();
  productsData.products = prod.productos;
  productsData.totalProducts = prod.totalProductos;
  return productsData;
};

export const findAllByPlatformAndSection = async (
  section,
  selectedPlataforma,
  skip,
  itemsPerPage
) => {
  const pResponse = await fetch(
    `${awsDns}/product/find-by-platform-and-category?platform_name=${selectedPlataforma}&category_name=${section}&skip=${skip}&limit=${itemsPerPage}`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    }
  );

  validResponse(pResponse);
  let productsData = {};
  const prod = await pResponse.json();
  productsData.products = prod.productos;
  productsData.totalProducts = prod.totalProductos;
  return productsData;
};
