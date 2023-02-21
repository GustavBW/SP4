const plain = new Map<string,string>();
const wasp = new Map<string,string>();
const dark = new Map<string,string>();

plain.set("--background-color", "#f5f5f5");
plain.set("--text-color", "#333");
plain.set("--text-highlight-color", "#000");
plain.set("--highlight-color", "rgba(0, 123, 255, 0.3)");
plain.set("--border-color", "#ccc");
plain.set("--image-drop-shadow", "none")

wasp.set("--background-color", "black");
wasp.set("--text-color", "#999");
wasp.set("--text-highlight-color", "white");
wasp.set("--highlight-color", "orange");
wasp.set("--border-color", "yellow");
wasp.set("--image-filter", "invert(.5)")

dark.set("--background-color", "#333");
dark.set("--text-color", "#999");
dark.set("--text-highlight-color", "white");
dark.set("--highlight-color", "grey");
dark.set("--border-color", "gray");
dark.set("--image-drop-shadow", "none")

export const CONFIGURATIONS = {
    plain: plain,
    wasp: wasp,
    dark: dark
}

export const applyConfiguration = (configuration: Map<string,string>) => {
    configuration.forEach((value, key) => {
        document.documentElement.style.setProperty(key, value);
    });
}