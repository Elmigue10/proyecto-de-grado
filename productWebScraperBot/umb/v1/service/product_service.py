from umb.v1.config import mongodb_config


def find_all(skip, limit):
    results = mongodb_config.find_all(skip, limit)
    brands = mongodb_config.find_brands()
    categories = mongodb_config.find_categories()
    platforms = mongodb_config.find_platforms()

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"],
        'marcas': brands,
        'categorias': categories,
        'plataformas': platforms
    }


def find_by_id(product_find_by_id_request):
    product = mongodb_config.find_by_id(product_find_by_id_request.product_id.strip())

    price_difference = 300000
    if product:
        price = int(product.get('precio', 0))
        category = product.get('categoria')

        min_price = str(price - price_difference)
        max_price = str(price + price_difference)

        similar = mongodb_config.find_by_price_range(min_price, max_price, category, 0, 10)

        return {
            'message': "OK",
            'status': 200,
            'producto': product,
            'similar': similar["products"]
        }

    else:
        return {
            'message': "NOT_FOUND",
            'status': 404
        }


def find_by_brand(brand_name, skip, limit):
    results = mongodb_config.find_by_brand(brand_name.strip(),
                                           skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_category(category_name, skip, limit):
    results = mongodb_config.find_by_category(category_name.strip(),
                                              skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_platform(platform_name, skip, limit):
    results = mongodb_config.find_by_platform(platform_name.strip(),
                                              skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_name(product_find_by_name_request):
    results = mongodb_config.find_by_name(product_find_by_name_request.product_name.strip(),
                                          product_find_by_name_request.skip,
                                          product_find_by_name_request.limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_price_range(min_price, max_price, category_name, skip, limit):
    results = mongodb_config.find_by_price_range(min_price.strip(),
                                                 max_price.strip(),
                                                 category_name.strip(),
                                                 skip, limit)
    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_brand_and_category(brand_name, category_name, skip, limit):
    results = mongodb_config.find_by_brand_and_category(brand_name.strip(),
                                                        category_name.strip(),
                                                        skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_platform_and_category(platform_name, category_name, skip, limit):
    results = mongodb_config.find_by_platform_and_category(platform_name.strip(),
                                                           category_name.strip(),
                                                           skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_brand_category_and_platform(brand_name, category_name, platform_name, skip, limit):
    results = mongodb_config.find_by_brand_category_and_platform(brand_name.strip(),
                                                                 category_name.strip(),
                                                                 platform_name.strip(),
                                                                 skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_ram_memory(ram_memory, category_name, skip, limit):
    results = mongodb_config.find_by_ram_memory(ram_memory.strip(), category_name.strip(),
                                                skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_storage_capacity(storage_capacity, category_name, skip, limit):
    results = mongodb_config.find_by_storage_capacity(storage_capacity.strip(), category_name.strip(),
                                                      skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_screen_size(screen_size, category_name, skip, limit):
    results = mongodb_config.find_by_screen_size(screen_size.strip(), category_name.strip(),
                                                 skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def find_by_id_list(product_find_id_list_request):
    results = mongodb_config.find_by_id_list(product_find_id_list_request.id_list,
                                             product_find_id_list_request.skip,
                                             product_find_id_list_request.limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'totalProductos': results["total_items"]
    }


def reorganize():
    return mongodb_config.reorganize()
