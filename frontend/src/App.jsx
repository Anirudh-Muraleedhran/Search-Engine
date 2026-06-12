import { useState } from "react";
import { search, phraseSearch } from "./services/searchService";
import "./App.css";

function App() {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const [phraseMode, setPhraseMode] = useState(false);

  const handleSearch = async () => {
    if (!query.trim()) return;

    setLoading(true);
    setError("");
    setResults([]);

    try {
      let data;

      if (phraseMode) {
        data = await phraseSearch(query);
      } else {
        data = await search(query);
      }

      setResults(data);
    } catch (err) {
      setError("Unable to reach search server.");
    } finally {
      setLoading(false);
    }
  };

  const highlightText = (text, query) => {
    if (!query.trim()) return text;

    const regex = new RegExp(`(${query})`, "gi");

    return text.split(regex).map((part, index) =>
      part.toLowerCase() === query.toLowerCase() ? (
        <mark key={index}>{part}</mark>
      ) : (
        part
      )
    );
  };

  return (
    <div className="app">
      <h1 className="heading">Mini Search Engine</h1>

      <p className="subtitle">
        Built using Spring Boot, React, Web Crawling, Inverted Indexing,
        TF-IDF Ranking and Phrase Search.
      </p>

      <input
        className="search-box"
        type="text"
        value={query}
        placeholder="Search..."
        onChange={(e) => setQuery(e.target.value)}
        onKeyDown={(e) => {
          if (e.key === "Enter") {
            handleSearch();
          }
        }}
      />

      <div className="controls">
        <label className="checkbox-label">
          <input
            type="checkbox"
            checked={phraseMode}
            onChange={(e) => setPhraseMode(e.target.checked)}
          />
          Exact Phrase Search
        </label>

        <button
          className="search-button"
          onClick={handleSearch}
        >
          Search
        </button>
      </div>

      <hr />

      {loading && (
        <p className="loading">
          Searching...
        </p>
      )}

      {error && (
        <p className="error">
          {error}
        </p>
      )}

      {!loading && results.length > 0 && (
        <p className="result-count">
          About {results.length} results found
        </p>
      )}

      {!loading &&
        results.length === 0 &&
        query &&
        !error && (
          <p className="no-results">
            No results found.
          </p>
        )}

      {results.map((result, index) => (
        <div
          className="result-card"
          key={index}
        >
          <p className="result-url">
            {result.url}
          </p>

          <a
            href={result.url}
            target="_blank"
            rel="noreferrer"
            className="result-title"
          >
            {result.title}
          </a>

          <p className="result-snippet">
            {highlightText(result.snippet, query)}
          </p>

          <p className="result-score">
            Score: {result.score.toFixed(2)}
          </p>
        </div>
      ))}
    </div>
  );
}

export default App;