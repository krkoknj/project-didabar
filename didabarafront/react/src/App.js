import "./App.css";
import Router from "./Router";
import { RecoilRoot } from "recoil";
import { ThemeProvider } from "styled-components";
import { Theme } from "./Theme";
import { QueryClientProvider, QueryClient } from "react-query";
import { Suspense } from "react";
import { createGlobalStyle } from "styled-components";
import { BrowserRouter } from "react-router-dom";

const queryClient = new QueryClient();
const GlobalStyle = createGlobalStyle`
*{
  box-sizing: border-box;
}
`;

function App() {
  return (
    <BrowserRouter>
      <QueryClientProvider client={queryClient}>
        <RecoilRoot>
          <Suspense fallback={<div>Loading</div>}>
            <ThemeProvider theme={Theme}>
              <GlobalStyle />
              <div style={{ height: "100vh" }}>
                <Router />
              </div>
            </ThemeProvider>
          </Suspense>
        </RecoilRoot>
      </QueryClientProvider>
    </BrowserRouter>
  );
}

export default App;
