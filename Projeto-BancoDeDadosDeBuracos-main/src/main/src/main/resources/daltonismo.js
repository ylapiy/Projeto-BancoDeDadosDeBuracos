document.addEventListener('DOMContentLoaded', function() {
    const select = document.getElementById("modo-daltonismo");
    
    const savedMode = localStorage.getItem("modoDaltonismo") || "normal";
    
    applyColorMode(savedMode);
    select.value = savedMode;
    
    select.addEventListener("change", function(e) {
        const modo = e.target.value;
        applyColorMode(modo);
        localStorage.setItem("modoDaltonismo", modo);
    });
    
    function applyColorMode(mode) {
        document.body.classList.remove('normal', 'protanopia', 'deuteranopia', 'tritanopia');
        
        document.body.classList.add(mode);
        
        updateMetaThemeColor(mode);
    }
    
    function updateMetaThemeColor(mode) {
        let themeColor;
        
        switch(mode) {
            case 'protanopia':
                themeColor = '#caa731';
                break;
            case 'deuteranopia':
                themeColor = '#e0b634';
                break;
            case 'tritanopia':
                themeColor = '#d4aa3c';
                break;
            default:
                themeColor = '#fdb710';
                break;
        }
        
        let metaTheme = document.querySelector('meta[name="theme-color"]');
        if (!metaTheme) {
            metaTheme = document.createElement('meta');
            metaTheme.name = 'theme-color';
            document.head.appendChild(metaTheme);
        }
        
        metaTheme.content = themeColor;
    }
    });