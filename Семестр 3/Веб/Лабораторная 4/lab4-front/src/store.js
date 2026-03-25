import { createStore } from 'redux';

const SET_X = 'SET_X';
const SET_Y = 'SET_Y';
const SET_R = 'SET_R';
const SET_RESULTS = 'SET_RESULTS';
const ADD_RESULT = 'ADD_RESULT';

export const setX = (x) => ({ type: SET_X, payload: x });
export const setY = (y) => ({ type: SET_Y, payload: y });
export const setR = (r) => ({ type: SET_R, payload: r });
export const setResults = (results) => ({ type: SET_RESULTS, payload: results });
export const addResult = (result) => ({ type: ADD_RESULT, payload: result });

const initialState = {
    x: null,
    y: 0,
    r: null,
    results: []
};

function rootReducer(state = initialState, action) {
    switch (action.type) {
        case SET_X:
            return { ...state, x: action.payload };
        case SET_Y:
            return { ...state, y: action.payload };
        case SET_R:
            return { ...state, r: action.payload };
        case SET_RESULTS:
            return { ...state, results: action.payload };
        case ADD_RESULT:
            return { ...state, results: [action.payload, ...state.results] };
        default:
            return state;
    }
}

const store = createStore(rootReducer);

export default store;