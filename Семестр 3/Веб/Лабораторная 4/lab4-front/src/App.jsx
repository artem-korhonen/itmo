import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import MainPage from "./MainPage"
import LoginPage from "./LoginPage"


import { getToken } from "./auth";


const PrivateRoute = ({ children }) => {
    const isAuthenticated = getToken();
    return isAuthenticated ? children : <Navigate to="/" replace />;
};


const PublicRoute = ({ children }) => {
    const isAuthenticated = getToken();
    return isAuthenticated ? <Navigate to="/main" replace /> : children;
};


function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={
                    <PublicRoute>
                        <LoginPage/>
                    </PublicRoute>
                } />

                <Route path="/main" element={
                    <PrivateRoute>
                        <MainPage/>
                    </PrivateRoute>
                } />
            </Routes>
        </BrowserRouter>
    )
}


export default App;