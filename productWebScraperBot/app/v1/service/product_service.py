from app.v1.config import mongodb_config


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


def find_by_id(prorduct_find_by_id_request):
    product = mongodb_config.find_by_id(prorduct_find_by_id_request.product_id)

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


def reorganize():
    return mongodb_config.reorganize()
