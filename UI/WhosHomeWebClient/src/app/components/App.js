import React from "react";
import List from "./List";
import Form from './Form'

const App = () => (
  <div className="row mt-5">
    <div className="col-md-4 offset-md-1">
    <h2>Articles</h2>
      <List />
      <Form />
    </div>
  </div>
);
export default App;