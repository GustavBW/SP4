const plain = new Map<string,string>();
const wasp = new Map<string,string>();
const dark = new Map<string,string>();
const beyond = new Map<string,string>();

plain.set("--text-color", "#999");
plain.set("--text-highlight-color", "#000");
plain.set("--highlight-color", "rgba(0, 123, 255, 0.3)");
plain.set("--border-color", "hsl(40, 70%, 50%)");
plain.set("--image-drop-shadow", "none")

wasp.set("--text-color", "#999");
wasp.set("--text-highlight-color", "white");
wasp.set("--highlight-color", "orange");
wasp.set("--border-color", "yellow");

dark.set("--text-color", "#999");
dark.set("--text-highlight-color", "white");
dark.set("--highlight-color", "grey");
dark.set("--border-color", "gray");
dark.set("--image-drop-shadow", "none")

beyond.set("--text-color", "#999");
beyond.set("--text-highlight-color", "white");
beyond.set("--border-color", "orange");


export const CONFIGURATIONS = {
    plain: plain,
    wasp: wasp,
    dark: dark,
    beyond: beyond
}

export const applyConfiguration = (configuration: Map<string,string>) => {
    configuration.forEach((value, key) => {
        document.documentElement.style.setProperty(key, value);
    });
}