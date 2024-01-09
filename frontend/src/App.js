import { Route, Routes } from "react-router-dom";
import HomePage from "./pages/Homepage";
import CreditCardFeature from "./pages/CreditCardFeature";
import LoginPage from "./pages/LoginPage";
import RegistrationPage from "./pages/RegistrationPage";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route index element={<LoginPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/registration" element={<RegistrationPage />} />
        <Route path='/home' element={<HomePage />} />
        <Route path='/credit-cards' element={<CreditCardFeature />} />
      </Routes>
    </div>
  );
}

export default App;
