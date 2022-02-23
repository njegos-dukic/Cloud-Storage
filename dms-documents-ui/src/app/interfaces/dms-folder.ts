import { DMSFile } from "./dms-file";

export interface DMSFolder {
    name: string;
    folders: DMSFolder[];
    files: DMSFile[];
}