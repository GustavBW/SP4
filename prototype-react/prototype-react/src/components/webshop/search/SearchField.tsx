import React, { useState } from 'react';
import './SearchField.css';
import SearchIcon from '../../../images/search.png'

interface SearchFieldProps {
    onSearch: (searchTerm: string) => void;
}

const SearchField: React.FC<SearchFieldProps> = ({ onSearch }) => {
    const [searchTerm, setSearchTerm] = useState('');

    const handleSearch = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        onSearch(searchTerm);
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(event.target.value);
    };

    return (
        <form onSubmit={handleSearch} className="SearchField">
            <div className="inputAndImage">
                <input type="text" value={searchTerm} onChange={handleInputChange} className="chip"/>
                <img src={SearchIcon} alt="Search" className="searchImage" />
            </div>
            
        </form>
    );
};

export default SearchField;