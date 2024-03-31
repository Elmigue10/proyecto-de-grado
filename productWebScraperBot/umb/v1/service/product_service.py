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
        'total_productos': results["total_items"],
        'marcas': brands,
        'categorias': categories,
        'plataformas': platforms
    }


def find_by_id(product_find_by_id_request):
    product = mongodb_config.find_by_id(product_find_by_id_request.product_id.strip())

    if product:
        return {
            'message': "OK",
            'status': 200,
            'producto': product
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
        'total_productos': results["total_items"]
    }


def find_by_category(category_name, skip, limit):
    results = mongodb_config.find_by_category(category_name.strip(),
                                              skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'total_productos': results["total_items"]
    }


def find_by_platform(platform_name, skip, limit):
    results = mongodb_config.find_by_platform(platform_name.strip(),
                                              skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'total_productos': results["total_items"]
    }


def find_by_name(product_name, skip, limit):
    results = mongodb_config.find_by_name(product_name.strip(),
                                          skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'total_productos': results["total_items"]
    }


def find_by_price_range(min_price, max_price, skip, limit):
    results = mongodb_config.find_by_price_range(min_price.strip(),
                                                 max_price.strip(),
                                                 skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'total_productos': results["total_items"]
    }


def find_by_brand_and_category(brand_name, category_name, skip, limit):
    results = mongodb_config.find_by_brand_and_category(brand_name.strip(),
                                                        category_name.strip(),
                                                        skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'total_productos': results["total_items"]
    }


def find_by_platform_and_category(platform_name, category_name, skip, limit):
    results = mongodb_config.find_by_platform_and_category(platform_name.strip(),
                                                           category_name.strip(),
                                                           skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'total_productos': results["total_items"]
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
        'total_productos': results["total_items"]
    }


def find_by_ram_memory(ram_memory, skip, limit):
    results = mongodb_config.find_by_ram_memory(ram_memory.strip(), skip, limit)

    return {
        'message': "OK",
        'status': 200,
        'productos': results["products"],
        'total_productos': results["total_items"]
    }


def reorganize():
    return mongodb_config.reorganize()
