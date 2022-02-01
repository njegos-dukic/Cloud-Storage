import * as internal from "stream";

export interface UserRegistrationDto {
    username: string;
    email: string;
    password: string;
}

export interface UserLoginDto {
    username: string;
    password: string;
}

export interface User {
    id: number;
    username: string;
    isManager: boolean;
    rootFolder: string;
    allowedDomain: string;
    canRead: boolean;
    canCreate: boolean;
    canUpdate: boolean;
    canDelete: boolean;
}