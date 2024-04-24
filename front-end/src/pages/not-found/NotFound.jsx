import {validateTokenWithRole} from "../../utilities/jwt-utilities";

const NotFound = () => {

    validateTokenWithRole("CLIENTE");

    return (
        <>
            <div className="not-found-container">
                <h1>404</h1>
                <h2>Resource Not Found</h2>
            </div>
        </>
    );
}

export default NotFound;