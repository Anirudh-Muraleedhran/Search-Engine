import { useState } from "react";
import { search } from "./services/searchService";

function App() {

  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);

  const handleSearch = async () => {

    if (!query.trim()) return;

    const data = await search(query);

    setResults(data);
  };
  return (
    <div style={{ padding: "30px" }}>

      <h1>Mini Search Engine</h1>

      <input
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        onKeyDown={(e) => {
        if (e.key === "Enter") { handleSearch();}}}placeholder="Search..."/>

      <button onClick={handleSearch}>
        Search
      </button>

      <hr />

      {results.map((result, index) => (
        <div key={index}>
          <h3>{result.title}</h3>

          <p>{result.snippet}</p>

          <small>{result.url}</small>

          <p>
            Score: {result.score}
          </p>

          <hr />
        </div>
      ))}
    </div>
  );
}

export default App;