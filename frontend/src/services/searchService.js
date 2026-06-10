import axios from "axios";

const API_URL = "http://localhost:8080";

export const search = async (query) => {
    const response = await axios.get(
        `${API_URL}/search?q=${query}`
    );

    return response.data;
};

export const phraseSearch = async (query) => {
    const response = await axios.get(
        `${API_URL}/phrase-search?q=${query}`
    );

    return response.data;
};