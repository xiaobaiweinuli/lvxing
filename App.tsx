import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import Footer from './components/layout/Footer';
import Home from './pages/Home';
import Features from './pages/Features';
import UIShowcase from './pages/UIShowcase';
import Technology from './pages/Technology';
import Guide from './pages/Guide';
import Download from './pages/Download';
import About from './pages/About';
import './App.css';

function App() {
  return (
    <Router>
      <div className="flex flex-col min-h-screen">
        <Navbar />
        <main className="flex-grow">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/features" element={<Features />} />
            <Route path="/ui-showcase" element={<UIShowcase />} />
            <Route path="/technology" element={<Technology />} />
            <Route path="/guide" element={<Guide />} />
            <Route path="/download" element={<Download />} />
            <Route path="/about" element={<About />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
