import React from 'react';

function App() {
  return (
      <div>
        <h1> Welcome react!! </h1>
        <form action="/sample" method="post">
            login: <input type="text" name="username" width="30"/>
            Password: <input type="text" name="password" width="30"/>
            <input type="submit" value="Login"/>
        </form>
      </div>
  );
}

export default App;
